import { Component, OnInit, Input } from '@angular/core';
import { User } from '../../models/user';

import { UserService} from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  @Input() user: User;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location
  ) {}

  ngOnInit() {
  }

  save(): void {
    this.userService.updateUser(this.user)
      .subscribe(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }

}
