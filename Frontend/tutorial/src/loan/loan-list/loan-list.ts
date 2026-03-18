import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LoanEdit } from '../loan-edit/loan-edit';
import { LoanService } from '../loan';
import { Loan } from '../Model/Loan';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ClientService } from '../../client/client';
import { Client } from '../../client/model/Client';
import { GameService } from '../../game/game';
import { Game } from '../../game/model/Game';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { PageEvent, MatPaginatorModule } from '@angular/material/paginator';
import { Pageable } from '../../app/core/model/page/Pageable';
import { DialogConfirmation } from '../../app/core/dialog-confirmation/dialog-confirmation';

@Component({
  selector: 'app-loan-list',
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
    MatPaginatorModule
  ],
  templateUrl: './loan-list.html',
  styleUrl: './loan-list.scss',
})
export class LoanList implements OnInit {
  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = ['id', 'gameTitle', 'clientName', 'loanDate', 'returnDate', 'action'];
  games: Game[] = [];
  clients: Client[] = [];
  filterGameTitle: number | null = null;
  filterClient: number | null = null;
  dateFilter: Date | null = null;
  loans: Loan[]; 
  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;
  
  constructor(
    private loanService: LoanService,
    private clientService: ClientService,
    private gameService: GameService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadPage(); 
  }

  ngAfterViewInit(): void {
    this.gameService.getGames().subscribe((games) => {
      this.games = games; 
      this.cdr.detectChanges();
    });

    this.clientService.getClients().subscribe((clients) => (this.clients = clients));
  }

  loadPage(event?: PageEvent) {
    const pageable: Pageable = {
        pageNumber: this.pageNumber,
        pageSize: this.pageSize,
        sort: [
            {
                property: 'id',
                direction: 'ASC',
            },
        ],
    };

    if (event != null) {
        pageable.pageSize = event.pageSize;
        pageable.pageNumber = event.pageIndex;
    }

    this.loanService.getLoansPage(pageable).subscribe((data) => {
        this.dataSource.data = data.content;
        this.pageNumber = data.pageable.pageNumber;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
    });
  }

  onCleanFilter(): void {
    this.filterGameTitle = null;
    this.filterClient = null;
    this.dateFilter = null;
    this.loadPage();
  }

  onSearch() {
    const gameId = this.filterGameTitle;
    const clientId = this.filterClient;
    const date: string | null = this.dateFilter ? this.formatDate(this.dateFilter) : null;

    const filters = {
      pageable: {
        pageNumber: this.pageNumber,
        pageSize: this.pageSize,
        sort: [
          {
            property: 'id',
            direction: 'ASC',
          },
        ],
      },
      gameId: this.filterGameTitle,
      clientId: this.filterClient,
      date: this.dateFilter ? this.formatDateForInput(this.dateFilter):null
      
    };
  console.log("FECHA QUE ENVÍO:", this.formatDateForInput(this.dateFilter));
    this.loanService.getLoansPageFiltered(filters).subscribe((data) => {
      
      data.content.forEach(loan => {
        loan.dateFrom = loan.dateFrom ? new Date(loan.dateFrom) : null;
        loan.dateTo   = loan.dateTo   ? new Date(loan.dateTo)   : null;
      });


      this.dataSource.data = data.content;
      this.pageNumber = data.pageable.pageNumber;
      this.pageSize = data.pageable.pageSize;
      this.totalElements = data.totalElements;
    });
  }
  
  createLoan(){ 
    const dialogRef = this.dialog.open(LoanEdit, {
      data: {},
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.loadPage();
    });
  }

  editLoan(loan: Loan){
    const dialogRef = this.dialog.open(LoanEdit, {
      data: { loan: loan },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.loadPage();
    });
  }

  deleteLoan(loan: Loan) {
    const dialogRef = this.dialog.open(DialogConfirmation, {
      data: {
        title: 'Confirmación',
        description: `¿Estás seguro de que deseas eliminar el préstamo del juego "${loan.game}" para el cliente "${loan.client}"?`,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loanService.deleteLoan(loan.id).subscribe(() => {
          this.loadPage();
        });
      }
    });
  }

  private formatDate(date: Date): string | null {
    if (!date) return null;
    const normalizedDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    const yyyy = normalizedDate.getFullYear();
    const mm = String(normalizedDate.getMonth() + 1).padStart(2, '0');
    const dd = String(normalizedDate.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
  }

  private formatDateForInput(date: Date | null): string | null {
    if (!date) return null;
    const d = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, '0');
    const dd = String(d.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
  }

  onDateChange(): void {
    const selectedDate = this.formatDateForInput(this.dateFilter);
    this.dateFilter = selectedDate ? new Date(selectedDate) : null;
  }

}
