import {Component, ElementRef, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {MiejsceService} from "../../services/miejsce/miejsce.service";
import {SeansService} from "../../services/seans/seans.service";
import {Miejsce} from "../../miejsce.model";
import {ActivatedRoute} from "@angular/router";
import {UserIdService} from "../../services/userId/user-id.service";
import {Rezerwacja} from "../../rezerwacja.model";
import {ReservationService} from "../../services/reservation/reservation.service";
import {Film} from "../../film.model";
import {FilmService} from "../../services/film/film.service";
import {BiletService} from "../../services/bilet/bilet.service";
import {Bilet} from "../../bilet.model";
import {debounceTime, delay} from "rxjs";

@Component({
  selector: 'app-sala-miejsca',
  templateUrl: './sala-miejsca.component.html',
  styleUrls: ['./sala-miejsca.component.css']
})
export class SalaMiejscaComponent implements OnInit {
  seansId: number;
  miejscaList!: Miejsce[];
  selectedMiejsca!: Miejsce[];
  rezerwacja!: Rezerwacja;
  rows!: Miejsce[][];
  displayMap = true;
  film!: Film;
  bilety: Bilet[] = [];
  sumaRezerwacji: number;


  constructor(private miejsceService: MiejsceService,
              private seansService: SeansService,
              private route: ActivatedRoute,
              private userService: UserIdService,
              private rezerwacjaService: ReservationService,
              private filmService: FilmService,
              private biletService: BiletService) { }

  ngOnInit(): void {
    this.seansId = parseInt(<string>this.route.snapshot.paramMap.get('id'));
    this.film = this.filmService.getSelectedFilm();
    console.log(this.film);
    this.miejsceService.getMiejscaBySeans(this.seansId).subscribe( data => {
      this.miejscaList = data;
      this.rows = this.miejscaList.reduce((acc, curr) => {
        acc[curr.rzad] = acc[curr.rzad] || [];
        acc[curr.rzad].push(curr);
        return acc;
      }, []);
      console.log(this.rows);
    });

    this.rezerwacjaService.createReservationForUserAndSeans(this.userService.getNumber(), this.seansId).subscribe(
      data => {
        this.rezerwacja = data;
        console.log('rezerwacja 1');
        console.log(this.rezerwacja);
      }
    );
    this.selectedMiejsca = [];

  }

  onSelectSeat(seatId: number){
    const selectedSeat: Miejsce = this.miejscaList.find( m => m.id === seatId);
    if (selectedSeat){
      const index = this.selectedMiejsca.indexOf(selectedSeat);
      if (index === -1) {
        this.selectedMiejsca.push(selectedSeat);
      } else {
        this.selectedMiejsca.splice(index, 1);
      }
    }
    console.log(this.selectedMiejsca);
  }

  toggleColor(event) {
    event.target.classList.toggle("clicked");
  }

  confirm() {
    for (const miejsce of this.selectedMiejsca) {

      this.biletService.createBiletForMiejsceAndRezerwacja(miejsce.id, this.rezerwacja.id)
        .pipe(delay(5000))
        .subscribe(data => {
          this.bilety.push(data);
        });
    }

    this.rezerwacjaService.getReservationById(this.rezerwacja.id)
      .subscribe(data => {
        this.rezerwacja = data;
        console.log(this.rezerwacja);
      });

    this.displayMap = false;
  }


  async setBiletTyp(bilet: Bilet, event: any) {
    console.log(event.target.value);
    const typ: string = event.target.value;
    const res = await this.biletService.setBiletTyp(bilet.id, typ).subscribe(
      data => {
        let index = this.bilety.findIndex(b => b.id === bilet.id);
        if(index > -1){
          this.bilety[index].cenabiletu = data.cenabiletu;
          console.log(data);
        }
      }
    );
    await this.updateRezerwacja()
  }

  async setMiejsceTyp(bilet: Bilet, event: any) {
    console.log(event.target.value);
    const typ: string = event.target.value;
    const res = await this.biletService.setBiletMiejsceTyp(bilet.id, typ).subscribe(
      data => {
        let index = this.bilety.findIndex(b => b.id === bilet.id);
        if(index > -1){
          this.bilety[index].cenabiletu = data.cenabiletu;
          console.log(data);
        }
      }
    );
    await this.updateRezerwacja();
  }

  async updateRezerwacja(){
    const res = await this.rezerwacjaService.getReservationById(this.rezerwacja.id).subscribe(
      data => {
        console.log(data);
        this.rezerwacja = data;
        this.sumaRezerwacji = this.rezerwacja.cenarezerwacji;
      }
    );
  }

}
