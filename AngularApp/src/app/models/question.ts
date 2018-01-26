export class Question {
  constructor(
    public id: number,
    public question_text: string,
    public active: boolean,
    public question_order: number
  ) {}
}
