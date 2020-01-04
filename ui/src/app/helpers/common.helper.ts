import { common } from './constant';

export class CommonHelper {
    public static validatePassword(password: string): boolean {
      var passwordPattern = RegExp(common.passwordPattern);
      return passwordPattern.test(password);
    }
}