import { createApp } from 'vue'
import './style.css'
import router from './router/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia'
import {mix} from './utils/mixin.js'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//暗黑模式
import 'element-plus/theme-chalk/dark/css-vars.css'
import App from './App.vue'

const app = createApp(App);

app.mixin(mix);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(createPinia())
app.use(ElementPlus)
app.use(router)
app.mount('#app')
