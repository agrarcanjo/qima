import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {AuthService} from "../../../shared/services/auth.service";
import {TokenStorageService} from "../../../shared/services/token-storage.service";
import {ProductService} from "../service/product.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {

  form: any = {
    id: null,
    name: null,
    description: null,
    category: null,
    available: null,
    price: null
  };

  categories: any[] = [];
  isLoggedIn = false;
  roles: string[] = [];

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private productService: ProductService,
    private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.getCategories();
      this.getProductEdit();
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  private getProductEdit(): void {
    const idProduct = this.activatedRoute.snapshot.paramMap.get('id');
    if (idProduct) {
      this.productService.getProduct(idProduct).subscribe(
        data => {
          const {id, name} = data.categories[0];
          this.form = {...data, category:  id};
          console.log(this.form);
        }
      );
    }
  }

  private getCategories(): void {
    this.productService.getAllCategories().subscribe(
      data => {
        this.categories = data;
      }
    );
  }

  onSubmit(): void {
    // if (this.form.id) {
    //   this.productService.editProduct(this.form).subscribe(
    //     data => {
    //       this.router.navigate(['home']);
    //     }
    //   )
    // } else {
    //   this.productService.createProduct(this.form).subscribe(
    //     data => {
    //       this.router.navigate(['home']);
    //     }
    //   )
    // }
    console.log(this.form);
    this.productService.createProduct(this.form).subscribe(
      data => {
        this.router.navigate(['home']);
      }
    );
  }
}
