import {Role} from './role';

export class Healthworker {

  constructor(
    public id: number,
    public username: string,
    public roles: Role[],
    public firstName: string,
    public lastName: string,
    public gender: string,
    public email: string,
    public phonenumber: string
  ) {}
}
