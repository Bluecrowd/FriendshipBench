import {Answer} from './answer';
import {Client} from './client';

export class Questionnaire {

  constructor(
    public id: number,
    public timestamp: string,
    public redflag: boolean,
    public client: Client,
    public answers: Answer[]
  ) {  }
}
