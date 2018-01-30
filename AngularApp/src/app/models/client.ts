import {Role} from './role';

export class Client {

  constructor(
    public id: number,
    public username: string,
    public roles: Role[],
    public firstName: string,
    public lastName: string,
    public birthDay: string,
    public gender: string,
    public age: number,
    public streetName: string,
    public province: string,
    public district: string,
    public email: string,
    public healthworker: string,
    public phonenumber: string,
    public housenumber: string,
  ) {}

}
