export interface QuizSubmissionQuestions {
  submissionId: any;
  questionId: any;
  sequence: number;
  sectionTitle: string;
  userAnswerKey: string;
  userAnswerContent: string;
  userAnswerReference: string;
  isCorrect: boolean;
  score: number;
  wagerLabel: string;
  wagerBonusScore: any;
  wagerPenaltyScore: any;
  startedOn: Date;
  finishedOn: Date;
}
