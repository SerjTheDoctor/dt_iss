import {Component, OnInit} from '@angular/core';
import {ConferenceService} from '../../services/conference.service';
import {Conference} from '../../models/conference.model';
import {NgbModal, NgbModalConfig, NgbModalModule} from '@ng-bootstrap/ng-bootstrap';
import { switchMap, map } from 'rxjs/operators';
import { combineLatest} from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { ProgramCommitteeService } from 'src/app/services/programCommittee.service';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-all-conferences',
  templateUrl: './all-conferences.component.html',
  styleUrls: ['./all-conferences.component.scss']
})

export class AllConferencesComponent implements OnInit {
  conferences: Conference[];
  result: Conference[];
  conference = new Conference();

  coChairEmail: string;
  coChairsEmails = [];

  constructor(
    private modalService: NgbModal,
    private router: Router,
    private conferenceService: ConferenceService,
    private authService: AuthService,
    private userService: UserService,
    private pcService: ProgramCommitteeService
  ) { }
  ngOnInit(): void {
    this.conferenceService.getConferences().subscribe(conf => {
      this.conferences = conf;
      this.result = conf;
    });
  }

  open(content) {
    this.modalService.open(content);
  }

  saveConference(): void{  
    console.log("Before save: " + JSON.stringify(this.conference));

    let coChairsAux: User[];
    let currentUserAux: User;

    this.authService.getCurrentUser().pipe(
      switchMap(currentUser => {
        console.log('CurrentUser');
        console.log(currentUser);
        currentUserAux = currentUser;
        const coChairsObservables = this.coChairsEmails.map(email => this.userService.getUserByEmail(email));
        return combineLatest(coChairsObservables);
      }),
      switchMap(coChairs => {
        coChairsAux = coChairs;
        console.log('CoChairs');
        console.log(coChairs);
        return this.conferenceService.createConference(this.conference.title, this.conference.description);
      }),
      switchMap(conference => {
        this.conference = conference;
        console.log('Conference');
        console.log(conference);
        return this.pcService.createProgramCommittee(currentUserAux, coChairsAux, this.conference.id);
      }),
    ).subscribe(
      pc => console.log(pc),
      err => console.error(err),
      () => this.router.navigate['/conference-page/' + this.conference.id]
    );

    // this.conferenceService.createConference(this.conference.title, this.conference.description).pipe(
    //   switchMap(conference => {
    //     this.conference = conference;
    //     console.log('Conference: ' + conference);
    //     const coChairsObservables = this.coChairsEmails.map(email => this.userService.getUserByEmail(email));
    //     return combineLatest(coChairsObservables);
    //   }),
    //   switchMap(coChairs => {
    //     coChairsAux = coChairs;
    //     console.log('CoChairs: ' + coChairs);
    //     return this.authService.getCurrentUser();
    //   }),
    //   switchMap(currentUser => {
    //     console.log('CurrentUser: ' + JSON.stringify(currentUser))
    //     return this.pcService.createProgramCommittee(currentUser, coChairsAux, this.conference.id);
    //   })
    // ).subscribe(pc => console.log('Program committee: ' + pc));
  }

  filter(event) {
    const filterKey = event.target.value;
    if (filterKey === '') {
      this.result = this.conferences;
    } else {
      // tslint:disable-next-line:max-line-length
      this.result = this.conferences.filter(conference => conference.title.search(filterKey) >= 0 || conference.description.search(filterKey) >= 0);
    }
  }
}
