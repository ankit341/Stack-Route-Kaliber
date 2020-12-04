import { Inject, Injectable } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { sequence } from '@angular/animations';
import { LoggerService } from './logger.service';
// key that is used to access the data in local storageconst

const STORAGE_KEY = 'question';
const QUIZ_STORAGE_KEY = 'quiz';
const TIMER_STORAGE_KEY = 'timerAndQuiz';
const QUESTIONDETAILS_STORAGE_KEY = 'questionDetails';
@Injectable()
export class LocalStorageService {
  anotherTodolist = [];
  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService,
              private logger: LoggerService) { }

  public storeOnLocalStorage(questionObj: any): void {
    const quizQuestion = this.storage.get(STORAGE_KEY) || [];
    quizQuestion.push({
      title: questionObj
    });
    // insert updated array to local storage
    this.storage.set(STORAGE_KEY, quizQuestion);
    this.logger.log(this.storage.get(STORAGE_KEY) || 'LocaL storage is empty');
  }
  public getFromLocalStorage() {
    return this.storage.get(STORAGE_KEY);
  }
  public removeOnLocalStorage(): void {
    this.storage.remove(STORAGE_KEY);
  }
  public storeQuizOnLocalStorage(quizObj: any): void {
    const quiz = this.storage.get(QUIZ_STORAGE_KEY) || [];
    quiz.push({
      title: quizObj
    });
    // insert updated array to local storage
    this.storage.set(QUIZ_STORAGE_KEY, quiz);
    this.logger.log(this.storage.get(QUIZ_STORAGE_KEY) || 'LocaL storage is empty');
  }
  public getQuizFromLocalStorage() {
    return this.storage.get(QUIZ_STORAGE_KEY);
  }
  public removeQuizOnLocalStorage(): void {
    this.storage.remove(QUIZ_STORAGE_KEY);
  }

  public storeTimerAndQuizDetails(systemTime, minutes, seconds, wager, isConfidence, title, id): void {
    const timerAndQuiz = this.storage.get(TIMER_STORAGE_KEY) || [];
    timerAndQuiz.push({
      timer: systemTime,
      durationMinutes: minutes,
      durationSeconds: seconds,
      confidence: wager,
      allowWager: isConfidence,
      quizTitle: title,
      submissionId: id
    });
    this.storage.set(TIMER_STORAGE_KEY, timerAndQuiz);
  }
  public getTimerAndQuizDetailsLocalStorage() {
    return this.storage.get(TIMER_STORAGE_KEY);
  }

  public removeTimerAndQuizDetailsLocalStorage(): void {
    this.storage.remove(TIMER_STORAGE_KEY);
  }

  public storeQuestionDetails(quesNumber, navigationMap, sequenceNum): void {
    const questionDetails = this.storage.get(QUESTIONDETAILS_STORAGE_KEY) || [];
    questionDetails.push({
      questionNumber: quesNumber,
      questionNumberArray: navigationMap,
      sequence: sequenceNum,
    });
    this.storage.set(QUESTIONDETAILS_STORAGE_KEY, questionDetails);
  }
  public getQuestionDetailsLocalStorage() {
    return this.storage.get(QUESTIONDETAILS_STORAGE_KEY);
  }
  public removeQuestionDetailsLocalStorage(): void {
    this.storage.remove(QUESTIONDETAILS_STORAGE_KEY);
  }
}
