import { Component, Inject, OnInit} from '@angular/core';
import { Loan } from '../Model/Loan';
import { LoanService } from '../loan';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Client } from '../../client/model/Client';
import { Game } from '../../game/model/Game';
import { ClientService } from '../../client/client';
import { GameService } from '../../game/game';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { OverlayModule } from '@angular/cdk/overlay';
import { ChangeDetectorRef } from '@angular/core';


@Component({
  selector: 'app-loan-edit',
  imports: [
    CommonModule, 
    MatTableModule, 
    MatButtonModule, 
    MatIconModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    OverlayModule,
  ],
  templateUrl: './loan-edit.html',
  styleUrl: './loan-edit.scss',
})
export class LoanEdit implements OnInit {
  loan: Loan;
  clients: Client[];
  games: Game[];
  loanClientId: number | null = null;
  loanGameId: number | null = null;
  dateFilterFrom: Date | null = null;
  dateFilterTo: Date | null = null;
  maxDate: Date | null = null;
  errorMessage: string = '';


  constructor(
    public dialogRef: MatDialogRef<LoanEdit>,
    private loanService: LoanService,
    private clientService: ClientService,
    private gameService: GameService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cdr: ChangeDetectorRef

  ) {}

  ngOnInit(): void {
    this.loan = this.data.loan ? Object.assign({}, this.data.loan) : new Loan();

    this.clientService.getClients().subscribe((clients) => {
      this.clients = clients;
      if (this.loan.client != null){
        const clientFilter: Client[] = clients.filter(
          (client) => client.id == this.data.loan.client.id
        );
        if (clientFilter != null){
          this.loan.client = clientFilter[0];
        }
      }
    });

    this.gameService.getGames().subscribe((games) => {
      this.games = games;
      if (this.loan.game != null){
        const gameFilter: Game[] = games.filter(
          (game) => game.id == this.data.loan.game.id
        );
        if (gameFilter != null){
          this.loan.game = gameFilter[0];
        }
      }
    });
    
      if (this.loan.dateFrom) {
        this.dateFilterFrom = new Date(this.loan.dateFrom);
        const max = new Date(this.dateFilterFrom);
        max.setDate(max.getDate() + 14);
        this.maxDate = max;
      }
      if (this.loan.dateTo) {
        this.dateFilterTo = new Date(this.loan.dateTo);
      }
    
  }

  onSave() {

    if (!this.loan.client || !this.loan.game || !this.dateFilterFrom || !this.dateFilterTo) {
      this.errorMessage = "Debe completar el campo vacío.";
      return;
    }

    const payload = {
      id: this.loan.id,
      client: this.loan.client,
      game: this.loan.game,
      dateFrom: this.formatDateForInput(this.dateFilterFrom),
      dateTo: this.formatDateForInput(this.dateFilterTo)
    }

    this.loanService.saveLoan(payload).subscribe({
      next: () => this.dialogRef.close(),
      error: (err) => {
        if (err.status === 409){
          this.loan.client = null;
          this.loan.game = null;
          this.loan.client = {...this.loan.client, name:''};
          this.loan.game = {...this.loan.game, title:''};
          this.errorMessage = "El cliente o el juego no tienen disponibilidad"
          this.cdr.detectChanges();
        }else{
           this.errorMessage = 'Error al guardar el cliente.';
        }
      }
    });
  }


  onClose() {
    this.dialogRef.close();
  }

  private formatDateForInput(date: Date | null): string | null {
    if (!date) return null;
    // Usa el constructor "numérico" que es local y NO UTC
    const d = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, '0');
    const dd = String(d.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
  }

  
  onDateChange(field: 'from' | 'to'): void {
    if (field === 'from') {
      const formatted = this.formatDateForInput(this.dateFilterFrom);
      this.dateFilterFrom = formatted ? new Date(formatted) : null;
      const max = new Date(this.dateFilterFrom);
      max.setDate(max.getDate() + 14);
      this.maxDate = max;      

    } else {
      const formatted = this.formatDateForInput(this.dateFilterTo);
      this.dateFilterTo = formatted ? new Date(formatted) : null;
    }
  }
  private formatDate(date: Date): string | null {
    if (!date) return null;
    const normalizedDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    const yyyy = normalizedDate.getFullYear();
    const mm = String(normalizedDate.getMonth() + 1).padStart(2, '0');
    const dd = String(normalizedDate.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
  }

}
