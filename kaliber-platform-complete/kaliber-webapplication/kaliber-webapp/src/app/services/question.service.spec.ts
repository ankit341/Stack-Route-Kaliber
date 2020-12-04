// import { TestBed, inject } from '@angular/core/testing';
// import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
// import { QuestionService } from './question.service';
// import { post } from 'selenium-webdriver/http';
// import { request } from 'http';

// describe('QuestionService', () => {
//   // tslint:disable-next-line: prefer-const
//   let service: QuestionService;
//   let httpMock: HttpTestingController;
//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       imports: [HttpClientTestingModule],
//       providers: [QuestionService]
//     });
//     service = TestBed.get(QuestionService);
//     httpMock = TestBed.get(HttpTestingController);
//   });

//   /* it('should be created', inject([QuestionService], (service: QuestionService) => {

//      expect(service).toBeTruthy();
//    }));*/
//   afterEach(() => {
//     httpMock.verify();
//   })
//   it('should  posts from the Api', () => {
//     const dummyPosts = {
//       questionTitle: 'hello question',
//       subject: 'hello question',
//       concept: 'hello question',
//       questionType: 123,
//       answerKeys: 123,
//       maxScore: 123,
//       maxDurationMins: 12,
//       difficultyLevel: 'hello question',
//       hintContent: 'hello question',
//       feedbackContent: 'hello question',
//       answerOptions: [],

//     };
//     service.postQuiz(dummyPosts).subscribe(post => {

//       expect(post).toEqual(this.dummyPosts);
//     });
//     const request = httpMock.expectOne(`${service.url}`);

//     expect(request.request.method).toBe('POST');
//     request.flush(dummyPosts);
//   });

// });
