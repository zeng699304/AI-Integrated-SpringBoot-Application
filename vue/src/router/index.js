import Vue from 'vue'
import VueRouter from 'vue-router'

//解决导航栏重复点击报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  //重定向到主页
    children: [
        { path: '/home', name: 'home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home.vue') },
        { path: '/user', name: 'user', meta: { name: '用户信息' },component: () => import('../views/manager/User.vue') },
        { path: '/403', name: 'auth', meta: { name: '无权限' },component: () => import('../views/manager/Auth.vue') },
        { path: '/person', name: 'person', meta: { name: '个人信息' },component: () => import('../views/manager/Person.vue') },
        { path: '/password', name: 'password', meta: { name: '修改密码' },component: () => import('../views/manager/Password.vue') },
        { path: '/yolov5', name: 'yolov5', meta: { name: '模型预测' },component: () => import('../views/manager/YOLOv5.vue') },
        { path: '/word', name: 'word', meta: { name: '文字识别' },component: () => import('../views/manager/Word.vue') },
        { path: '/nlp', name: 'nlp', meta: { name: '文本纠错' },component: () => import('../views/manager/NLP.vue') },
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue'),
    meta: { name: '登录' }
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue'),
    meta: { name: '注册' }
  },
  {
    path: '*',
    name: '404',
    component: () => import('../views/404.vue'),
    meta: { name: '无法访问' }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  console.log(to, from, next)
  //to表示要进入的路由对象
  //from表示离开的路由对象
  //next表示执行下一步操作
  let adminPaths = ['/user']
  let user = JSON.parse(localStorage.getItem('honey-user')||'{}')
  if(user.role!=='管理员'&&adminPaths.includes(to.path)) {  //如果用户不是管理员，且要进入管理员页面
    next('/403')
  } else {
    next()  //放行
  }
})

export default router
