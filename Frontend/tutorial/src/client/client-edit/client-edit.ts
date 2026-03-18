import { Component, OnInit, Inject, NgZone, ChangeDetectorRef } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClientService } from '../client';
import { Client } from '../model/Client';
import { FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-client-edit',
  imports: [
    FormsModule, 
    ReactiveFormsModule, 
    MatFormFieldModule, 
    MatInputModule, 
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './client-edit.html',
  styleUrl: './client-edit.scss',
})
export class ClientEdit implements OnInit{
    client: Client;
    namePlaceholder: string = 'Nombre del cliente';
    duplicateNameError: boolean = false;
    errorMessage: string = '';
    emptyError: boolean = false;
    duplicateError: boolean = false;
    saving: boolean = false;
    constructor(
        public dialogRef: MatDialogRef<ClientEdit>,
        @Inject(MAT_DIALOG_DATA) public data: { client: Client },
        private clientService: ClientService,
        private zone: NgZone,
        private cd: ChangeDetectorRef
    ) {}

    ngOnInit(): void {
        this.client = this.data.client != null ? Object.assign({}, this.data.client) : new Client();
    }

    onSave(form?: NgForm) {
      
      if (!this.client.name || this.client.name.trim() === '') {
        this.defer(() => {
          this.errorMessage = 'El nombre no puede estar vacío.';
        });
        return;
      }

      this.clientService.saveClient(this.client).subscribe({
        next: ()=> {
          this.defer(() => {
            this.dialogRef.close();
          });
        },
        error: (err) => {
          if (err.status === 409) { // Conflict - nombre duplicado
            this.defer(() => {
              this.client = { ...this.client, name: '' }; // Limpiar el campo de nombre
              this.errorMessage = 'El nombre del cliente ya existe.';
            });
          }else {
            this.defer(() => {
              this.errorMessage = 'Error al guardar el cliente.';
            });
          }
        }
      });
    }

    private defer(fn: () => void) {
        this.zone.run(() => {
            setTimeout(() => {
                fn();
                this.cd.detectChanges();
            }, 0);
        });
    }


    onClose() {
        this.dialogRef.close();
    }
}

