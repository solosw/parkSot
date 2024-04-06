
// #ifndef VUE3
import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false
Vue.prototype.request = "http://localhost:8080/user"
Vue.prototype.systemRequest = "http://localhost:8080/api"
App.mpType = 'app'
// 设置响应拦截器
uni.addInterceptor('request', {
  invoke(args) {
	
	
  },
  success(args) {
   
  }, 
  fail(err) {
    uni.showToast({
    	title:"网络错误",
		icon:"error"
    })
  }, 
  complete(res) {
   
  },
  returnValue(res) {
  	return res
  }
})


const app = new Vue({
	...App
})
app.$mount()
// #endif

// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
import App from './App.vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
Vue.prototype.request = "http://localhost:8080/user"
Vue.prototype.systemRequest = "http://localhost:8080/api"
// #endif