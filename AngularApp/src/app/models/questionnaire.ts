import {Question} from './question';
import {Answer} from './answer';
import DateTimeFormat = Intl.DateTimeFormat;
import {Client} from './client';

export class Questionnaire {

  constructor(
    public id: number,
    public timeStamp: DateTimeFormat,
    public redFlag: boolean,
    public client: Client,
    public questions: Question[],
    public answers: Answer[]
  ) {  }
}
