import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/event';
const COMMENT_URL = 'http://localhost:8080/api/comment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  getEvents(): Observable<any> {
    return this.http.get(API_URL);
  }

  saveEvent(name: string, location: string, description: string, time :string): Observable<any> {
    const data = {
       location,
       description,
       time,
       name
     };
     return this.http.post(API_URL, data );
   }


   deleteEvent(id: number): Observable<any> {
     return this.http.delete(API_URL+"/"+id);
   }

  getEvent(id: any): Observable<any> {
    return this.http.get(API_URL+"/"+id);
  }

  getEventParticipants(id: any): Observable<any> {
    return this.http.get(API_URL+"/participant/"+id);
  }

  approve(id: any): Observable<any> {
    return this.http.post(API_URL+"/approve/"+id, {});
  }

  joining(eventId: any): Observable<any> {
    return this.http.post(API_URL+"/joining/"+eventId, {});
  }

  request(id: any): Observable<any> {
    return this.http.post(API_URL+"/request/"+id, {});
  }

  updateOrganizer(eventId: any, visitorId: any): Observable<any> {
    return this.http.get(API_URL+"/organizer/"+eventId+"/"+visitorId, {});
  }  

  postComment(comment: string, eventId: number): Observable<any> {
    const data = { 
      "eventId":eventId,
      "comment":comment       
    };   

    return this.http.post(COMMENT_URL, data);
  }  


  searchEvents(searchString:string): Observable<any> {
    return this.http.get(API_URL+"?searchString="+searchString);
  }

}
