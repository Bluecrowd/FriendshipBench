import {Answer} from './answer';
import {Client} from './client';

export class Questionnaire {

  constructor(
    public id: number,
    public timeStamp: Date,
    public redFlag: boolean,
    public client: Client,
    public answers: Answer[]
  ) {  }
}
