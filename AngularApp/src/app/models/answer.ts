import {QuestionnairesService} from '../services/questionnaires.service';
import {Question} from './question';

export class Answer {

  constructor(
    public id: number,
    public answer: boolean,
    public question: Question
  ) {  }
}
