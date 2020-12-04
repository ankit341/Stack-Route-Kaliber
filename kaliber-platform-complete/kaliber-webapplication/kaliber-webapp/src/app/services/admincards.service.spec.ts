import { TestBed, ComponentFixture } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { Observable, from } from 'rxjs'; import { AdmincardsService } from './admincards.service';
import { AdminquizbankComponent } from '../home/adminquizbank/adminquizbank.component'; describe('AdmincardsService', () => {
    // tslint:disable-next-line: prefer-const
    let service: AdmincardsService;
    // tslint:disable-next-line: prefer-const
    let component: AdminquizbankComponent;
    // tslint:disable-next-line: prefer-const
    let fixture: ComponentFixture<AdminquizbankComponent>; beforeEach(() => TestBed.configureTestingModule({
        imports: [HttpClientModule],
        providers: [AdmincardsService]
        // declarations: [ AdminquizbankComponent ]
    }));
    // it('service is working fine', () => {
    //     // tslint:disable-next-line: no-shadowed-variable
    //     const service: AdmincardsService = TestBed.get(AdmincardsService);
    //     expect(service).toBeTruthy();
    // });
    // it("should create a post in an array", () => {
    //   const qouteText = "This is my first post";
    //   service.getQuizzes();
    //   expect(service.Quizzes.length).toBeGreaterThanOrEqual(1);
    // });//   it('should create the app', () =>
    // {
    // const fixture = TestBed.createComponent(AdminquizbankComponent);
    // const app = fixture.debugElement.componentInstance;
    // expect(app).toBeTruthy();
    // });  // it("should use the Quizzes from the service", () => {
    //   const service = fixture.debugElement.injector.get(AdmincardsService);
    //   fixture.detectChanges();
    //   expect(service.getQuizzes()).toEqual(component.Quizzes);
    // });
});
