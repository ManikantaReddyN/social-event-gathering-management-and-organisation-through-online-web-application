import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-view-event',
  templateUrl: './view-event.component.html',
  styleUrls: ['./view-event.component.css']
})
export class ViewEventComponent implements OnInit { 
  
  event?: any;
  content?: any;
  currentUserId?:any;
  currentUserEvent?:any;
  requested:boolean = false;
  approved:boolean = false;
  goining:boolean= false;
  goingTotal:number=0;
  isRequested:boolean=false;
  isFailed:boolean=false;

  constructor(private userService: UserService,  private activatedRoute: ActivatedRoute, private tokenService: TokenStorageService) { }

  ngOnInit(): void {   
    this.currentUserId = this.tokenService.getUser().id;
    
    this.activatedRoute.paramMap.subscribe((data)=>{
      
       this.userService.getEvent(data.get("id")).subscribe(
        data => {
          this.event = data;  
           this.currentUserEvent = (data.organizer.user.id   == this.currentUserId);
              },
        err => {
          this.event = JSON.parse(err.error).message;
        }
      );
 
      this.userService.getEventParticipants(data.get("id")).subscribe(
        data => {
          this.content = data;         
         
        },
        err => {
          // this.content = JSON.parse(err.error).message;
        }
      ); 
      
    })
  }

  approve(id: number): void{
    this.userService.approve(id).subscribe(
      data => {
        window.location.reload();
      },
      err => {
        // this.content = JSON.parse(err.error).message;
      }
    );
  }

  request(id: number): void{
    this.isRequested = true;
    this.userService.request(id).subscribe(
      data => {
        window.location.reload();       
      },
      err => {
        // this.content = JSON.parse(err.error).message;
        this.isFailed = true;       
      }
    );
  }

  joining(eventId: number): void{
    this.userService.joining(eventId).subscribe(
      data => {
        window.location.reload();        
      },
      err => {
        // this.error = "You alread have event at the same time";
      }
    );
  }


  updateOrganizer(eventId: any, visitoriId: any): void{
    this.userService.updateOrganizer(eventId, visitoriId).subscribe(
      data => {
        window.location.reload();
      },
      err => {
        // this.content = JSON.parse(err.error).message;
      }
    );
  }

  alreadyRequested(): boolean{       
    this.content.forEach( (element:any) => {     
    this.requested =  this.requested || (element.participant.user.id == this.currentUserId);      
    });
    return this.requested;
  }

  requestApproved(): boolean{  
    this.content.forEach( (element:any) => 
    {
     this.approved =  this.approved || (element.approved && (element.participant.user.id == this.currentUserId));
  });
    return this.approved || this.currentUserEvent;
  }

  alreadyGoing(): boolean{  
    this.content.forEach( (element:any) => {
     this.goining =  this.goining || (element.joining && (element.participant.user.id == this.currentUserId)) ;     
     this.goingTotal += element.joining?1:0; 
  });
    return this.goining;
  }
  
  post(comment:string, eventId: number):void{
    
    this.userService.postComment(comment, eventId).subscribe(
      data => {
        window.location.reload();
      },
      err => {        
        // this.content = JSON.parse(err.error).message;
      }
    ); 
  }
 
}
