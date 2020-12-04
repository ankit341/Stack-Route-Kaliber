export interface Question {
  questionTitle: string;
  subject: string;
  concepts: any;
  QuestionType: any;
  answer: any;
  maxScore: any;
  maxDurationMins: number;
  DifficultyLevel: string;
  hintContent: string;
  feedbackContent: string;
  answerOptions?: any;
  isCorrect: boolean;
}
