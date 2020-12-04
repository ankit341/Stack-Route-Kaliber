export interface QuizSubmission {
  submissionId: any;
  quizCode: string;
  correctlyAnsweredQuestions: number;
  incorrectAnsweredQuestions: number;
  unattemptedQuestions: number;
  totalQuestions: number;
  userName: string;
  totalPointsScored: number;
  quizStartTime: Date;
  quizEndTime: Date;
  quizEvaluationTime: Date;
  createdOn: Date;
  quizTitle: string;
 }
