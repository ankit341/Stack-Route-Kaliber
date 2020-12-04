
import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service'; describe('UserService', () => {
    beforeEach(() => TestBed.configureTestingModule({
        imports: [
            HttpClientTestingModule,
        ],
        providers: [UserService]
    }));
    //   it('should be created', () => {
    //     const service: UserService = TestBed.get(UserService);
    //     expect(service).toBeTruthy();
    //   });
    it('User service is Running Fine', inject([UserService], (service: UserService) => {
        expect(service).toBeTruthy();
    }));
});
