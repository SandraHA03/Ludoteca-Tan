import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Loan } from './Model/Loan';
import { HttpClient } from '@angular/common/http';
import { Pageable } from '../app/core/model/page/Pageable';
import { LoanPage } from './Model/LoanPage';

@Injectable({
  providedIn: 'root',
})
export class LoanService {
  constructor(
    private http: HttpClient
  ) {}

  private baseUrl = 'http://localhost:8080/loan';
  
  getLoans(titleId?: number, clientId?: number, date?: string): Observable<Loan[]> {
    return this.http.get<Loan[]>(this.composeFindUrl(titleId, clientId, date));
  }

  getLoansPage(pageable: Pageable): Observable<LoanPage> {
    return this.http.post<LoanPage>(this.baseUrl, {pageable: pageable});
  }

  getLoansPageFiltered(filters:any): Observable<LoanPage> {
    return this.http.post<LoanPage>(`${this.baseUrl}`, filters);
  }

  saveLoan(loan: Loan): Observable<void> {
    const { id } = loan;
    const url = id ? `${this.baseUrl}/${id}` : this.baseUrl;

    return this.http.put<void>(url, loan);
  }

  private composeFindUrl(titleId?: number, clientId?: number, date?: string): string {
    const params = new URLSearchParams();
    if (titleId) {
      params.set('titleId', titleId.toString());
    }
    if (clientId) {
        params.set('idClient', clientId.toString());
    }
    if (date) {
      params.set('date', date);
    }
    const queryString = params.toString();
    return queryString ? `${this.baseUrl}?${queryString}` : this.baseUrl;
  }

  deleteLoan(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}


