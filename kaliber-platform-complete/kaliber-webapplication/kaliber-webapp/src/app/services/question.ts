


export interface Question {

  questionTitle: string;
  subject: string;
  concept: string;
  questionType: any;
  answerKeys: any;
  maxScore: any;
  maxDurationMins: number;
  difficultyLevel: any;
  hintContent: string;
  feedbackContent: string;
  answerOptions ?: any;
}
