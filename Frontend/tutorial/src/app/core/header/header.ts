import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';

    @Component({
        selector: 'app-header',
        standalone: true,
        imports: [
            CommonModule,
            RouterModule,
            MatIconModule, 
            MatToolbarModule,
        ],
        templateUrl: './header.html',
        styleUrl: './header.scss'
    })
    export class HeaderComponent {

    }
