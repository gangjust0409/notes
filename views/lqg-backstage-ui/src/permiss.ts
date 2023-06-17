import router from './router';
import menuStore from './store/menu'

router.beforeEach(async (to, from, next) => {
    const store = menuStore();
    if (to.fullPath.indexOf('login') === -1) {
        await store.loadMenus();
        // store.getterMenus.forEach((route: any) => {
        //     router.addRoute({
        //         path: '/layout/' + route.path,
        //         component: getComponent(route),
        //         name: route.title
        //     });
        // })
        next();

    } else {

        next();
    }
});


function getComponent(route: any): any {
    if (route.component != null) {
        return () => import(`../pages/${route.component}`)
    } else {
        return 'layout';
    }
}