import { NgModule } from '@angular/core';

import { ModellingNoSqlSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ModellingNoSqlSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ModellingNoSqlSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ModellingNoSqlSharedCommonModule {}
