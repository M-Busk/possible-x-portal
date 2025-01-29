import {ActivatedRouteSnapshot, ResolveFn, Router, RouterStateSnapshot} from '@angular/router';
import {ApiService} from "../api/api.service";
import {inject} from "@angular/core";

export const isAuthenticated: ResolveFn<any> =
  async (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> => {
    const router = inject(Router);
    const apiService = inject(ApiService);

    try {
      const response = await apiService.getAllRegistrationRequests();
      return true;
    } catch (e) {
      console.log(e);
      if (e.status === 401 || e.status === 403) {
        switch (e.status) {
          case 401:
            alert(`Invalid Credentials used. Please log in again.`);
            break;
          case 403:
            alert(`Unauthorized Credentials used. Please log in with the correct Role.`);
            break
        }
        router.navigate(["/login"]).then(() => {
          window.location.reload();
        });
      }
      return false;
    }
  }
