export class RegisterForm {
  birthDay: string;
  email: string;
  firstName: string;
  gender: string;
  lastName: string;
  password: string;
  phonenumber: string;
  username: string;

  setBirthday(birthday): void {
    this.birthDay = birthday + 'T00:00:00Z';
  }
}
