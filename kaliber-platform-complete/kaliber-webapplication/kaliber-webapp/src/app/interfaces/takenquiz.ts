export interface Takenquiz {
    submissionId: number;
    userName: string;
    quizCode: string;
    totalQuestions: number;
    correctlyAnsweredQuestions: number;
    incorrectAnsweredQuestions: number;
    totalPointsScored: number;
    sections: [ ];
   }
