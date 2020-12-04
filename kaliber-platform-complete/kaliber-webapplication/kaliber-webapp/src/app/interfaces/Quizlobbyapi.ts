export interface Quizlobbyapi {
    id: string;
    quizCode: string;
    title: string;
    maxDurationMinutes: number;
    maxScore: number;
    sections: any[];
    concepts: any[];
    totalQuestions: number;
    allowWager: boolean;
    confidenceWager: any[];
    days: any;
}
