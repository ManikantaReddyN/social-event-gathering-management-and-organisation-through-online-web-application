import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  content?: any;
  currentUserId?:any;

  constructor(private userService: UserService, private tokenService: TokenStorageService) { }

  ngOnInit(): void {
    this.currentUserId = this.tokenService.getUser().id;

    this.userService.getEvents().subscribe(
      data => {
        this.content = data;      
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

  deleteEvent(id: number): void{
    this.userService.deleteEvent(id).subscribe(
      data => {
        window.location.reload();
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }


  currentUserEvent(organizerUserId:number): boolean {
    console.log(this.currentUserId);
    console.log(organizerUserId);
    return this.currentUserId == organizerUserId;
  }

  searchEvent(searchString: string): void{
    this.userService.searchEvents(searchString).subscribe(
      data => {
        this.content = data;      
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

  resetSearch():void{
    this.userService.getEvents().subscribe(
      data => {
        this.content = data;      
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }
}
