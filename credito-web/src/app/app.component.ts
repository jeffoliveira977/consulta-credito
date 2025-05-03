import { Component } from '@angular/core';
import { ConsultaCreditoComponent } from './components/consulta-credito.component';

@Component({
  selector: 'app-root',
  imports: [ConsultaCreditoComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'credito-web';
}
