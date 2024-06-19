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
  isLoginFailed = false;
  errorMessage = '';
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
      this.getProductEdit();
      this.getCategories();
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  private getProductEdit() {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    if (id) {
      this.productService.getProduct(id).subscribe(
        data => {
          this.form = data;
        }
      )
    }
  }

  private getCategories() {
    this.productService.getAllCategories().subscribe(
      data => {
        this.categories = data;
      }
    )
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
    this.productService.createProduct(this.form).subscribe(
      data => {
        this.router.navigate(['home']);
      }
    )
  }
}
