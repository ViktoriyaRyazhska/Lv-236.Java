package com.inva.hipstertest.batch;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.domain.enums.NotificationType;
import com.inva.hipstertest.domain.enums.SampleView;
import com.inva.hipstertest.repository.*;
import com.inva.hipstertest.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by slavkosoltys on 14.07.17.
 */
@Component
public class AverageGradeBatchJob {

    /**
     * every 20:00:00, working days.
     */
    private static final String CRON_EXPRESSION = "0 0 19 ? * WED";

    private static final String NOTIFICATION_MESSAGE_TEMPLATE = "%s %s, school: %s, form: %s, lesson: %s, average rating: %.2f";
    private static int averageGradeThreshold = 7;

    @Autowired
    private PupilRepository pupilRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private AttendancesRepository attendancesRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MailService mailService;

    /**
     * Analyzes pupils attendances and create notifications for related users (parents and teacher)
     * if average grade is less than threshold.
     */
    @Scheduled(cron = CRON_EXPRESSION)
    public void run() {
        formRepository.findAll().forEach(form -> {
            List<Lesson> lessons = lessonRepository.getDistinctLessonsByFormId(form.getId());
            pupilRepository.findAllByFormId(form.getId()).forEach(pupil -> {
                ZonedDateTime today = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
                analyzePupilGradesByLessons(pupil, lessons, getStartDate(SampleView.QUARTER, today), today);
            });
        });
    }

    private void analyzePupilGradesByLessons(Pupil pupil, List<Lesson> lessons, ZonedDateTime startDate, ZonedDateTime today) {
        lessons.forEach(lesson ->
            Stream.of(getPupilAttendances(pupil, startDate, today, lesson).stream()
                .mapToInt(Attendances::getGrade)
                .filter(grade -> grade != 0)
                .average()
            ).filter(OptionalDouble::isPresent)
                .mapToDouble(OptionalDouble::getAsDouble)
                .filter(average -> average < averageGradeThreshold)
                .forEach(average -> informUsers(pupil, prepareNotificationString(pupil, lesson, average)))
        );
    }

    private List<Attendances> getPupilAttendances(Pupil pupil, ZonedDateTime startDate, ZonedDateTime today, Lesson lesson) {
        return attendancesRepository.findAllByPupilIdAndLessonIdAndDateBetween(pupil.getId(), lesson.getId(), startDate, today);
    }

    private ZonedDateTime getStartDate(SampleView view, ZonedDateTime today) {
        switch (view) {
            case WEEK:
                return today.minusWeeks(1);
            case QUARTER:
                return today.minusMonths(3);
            case MONTH:
            default:
                return today.minusMonths(1);
        }
    }

    private String prepareNotificationString(Pupil pupil, Lesson lesson, double average) {
        return String.format(NOTIFICATION_MESSAGE_TEMPLATE, pupil.getUser().getFirstName(), pupil.getUser().getLastName(),
           pupil.getForm().getSchool().getName(), pupil.getForm().getName(), lesson.getName(), average);
    }

    private void informUsers(Pupil pupil, String message) {
        List<Parent> parents = parentRepository.findAllByPupilId(pupil.getId());
        parents.forEach(parent ->
            mailService.sendSimpleEmail(parent.getUser().getEmail(), message, NotificationType.GRADE)
        );
        List<Notification> notifications = parents.stream().map(parent ->
            Notification.builder()
                .user(parent.getUser())
                .message(message)
                .type(NotificationType.GRADE)
                .build()
        ).collect(Collectors.toList());

        Form form = pupil.getForm();
        Teacher teacher = form.getTeacher();
        if (teacher != null) {
            notifications.add(Notification.builder()
                .user(teacher.getUser())
                .message(message)
                .type(NotificationType.GRADE)
                .build());
        }
        notificationRepository.save(notifications);
    }
}
