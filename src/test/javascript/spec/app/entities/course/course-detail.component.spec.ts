import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SchoolNetTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CourseDetailComponent } from '../../../../../../main/webapp/app/entities/course/course-detail.component';
import { CourseService } from '../../../../../../main/webapp/app/entities/course/course.service';
import { Course } from '../../../../../../main/webapp/app/entities/course/course.model';

describe('Component Tests', () => {

    describe('Course Management Detail Component', () => {
        let comp: CourseDetailComponent;
        let fixture: ComponentFixture<CourseDetailComponent>;
        let service: CourseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SchoolNetTestModule],
                declarations: [CourseDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CourseService,
                    EventManager
                ]
            }).overrideComponent(CourseDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CourseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Course(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.course).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
