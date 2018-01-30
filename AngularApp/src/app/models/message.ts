export class Message {

  constructor(public id: number,
              public user_id: string,
              public user_name: string,
              public message: string,
              public time: string,
              public room: string) {
  }
}
