import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SchoolNetTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AttendancesLogDetailComponent } from '../../../../../../main/webapp/app/entities/attendances-log/attendances-log-detail.component';
import { AttendancesLogService } from '../../../../../../main/webapp/app/entities/attendances-log/attendances-log.service';
import { AttendancesLog } from '../../../../../../main/webapp/app/entities/attendances-log/attendances-log.model';

describe('Component Tests', () => {

    describe('AttendancesLog Management Detail Component', () => {
        let comp: AttendancesLogDetailComponent;
        let fixture: ComponentFixture<AttendancesLogDetailComponent>;
        let service: AttendancesLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SchoolNetTestModule],
                declarations: [AttendancesLogDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AttendancesLogService,
                    EventManager
                ]
            }).overrideComponent(AttendancesLogDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttendancesLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendancesLogService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AttendancesLog(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.attendancesLog).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
