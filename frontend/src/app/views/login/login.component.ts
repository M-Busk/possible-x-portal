import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/mgmt/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router, private auth: AuthService)  {}

  login(username: string, password: string) {
    var authToken = btoa(username + ':' + password);
    this.auth.login(username, password);
    this.username = '';
    this.password = '';
    this.router.navigate(['/administration/management']).then(() => {
      window.location.reload();
    });
  }
}
