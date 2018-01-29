import { Component, OnInit } from '@angular/core';
import {ClientsService} from '../../services/clients.service';
import {Client} from '../../models/client';

declare var $: any;

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {
  clients: Client[];


  constructor(private clientsService: ClientsService) { }

  ngOnInit() {
  }

  getClients(): void {
    this.clientsService.getClients()
      .subscribe(clients => { this.clients = clients; setTimeout(() => { $('#dataTable').DataTable(); }, 350); } );
  }

}
