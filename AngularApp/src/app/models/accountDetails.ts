import {RoleName} from './roleName';

export class AccountDetails {
  id: number;
  username: string;
  roles: [RoleName];
  firstName: string;
  lastName: string;
  birthDay: string;
  email: string;
  phonenumber: string;
}
