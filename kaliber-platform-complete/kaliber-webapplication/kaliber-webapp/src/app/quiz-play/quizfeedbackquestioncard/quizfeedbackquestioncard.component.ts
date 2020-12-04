
import { Component, OnInit, Input } from '@angular/core';
import { QuizSubmissionService } from 'src/app/services/quiz-submission.service';
import { QuizfetchService } from 'src/app/services/quizfetch.service';
import { LoggerService } from 'src/app/services/logger.service';

@Component({
  selector: 'app-quizfeedbackquestioncard',
  templateUrl: './quizfeedbackquestioncard.component.html',
  styleUrls: ['./quizfeedbackquestioncard.component.scss']
})
export class QuizfeedbackquestioncardComponent implements OnInit {
  playedQuestionObj: any;
  playedQuestionObjData: any;
  constructor(private quizSubmission: QuizSubmissionService, private fetch: QuizfetchService,
              private logger: LoggerService) { }
  @Input() quesData;
  isActive = false;
  ngOnInit() {
    this.logger.log('inside feedback card');
    this.logger.log('before sort', this.quesData);
    // tslint:disable-next-line: only-arrow-functions
    this.quesData.sort(function(a, b) {
      return a.sequence - b.sequence;
    });
    this.logger.log('quesData sorted', this.quesData);
    // const qId = this.quesData.questionId;
    // this.fetch.getQuestions(`kaliber-quiz-play/questions/${this.quesData.questionId}`).subscribe(
    //    questionData => {
    //     this.playedQuestionObjData = questionData;
    //     this.playedQuestionObj = this.playedQuestionObjData.result;
    //     this.logger.log('played obj from card', this.playedQuestionObj);
    //    });

}
}
