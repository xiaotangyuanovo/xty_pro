import { mount, createLocalVue } from '@vue/test-utils';
import Vuex from 'vuex';
import Home from '@/views/Home.vue';

const localVue = createLocalVue();
localVue.use(Vuex);

describe('Home.vue router', () => {
  it('pushes Courses route when course card is clicked', async () => {
    const courses = [{ id: 1, title: 'Test', teacher: 'T', cover: '' }];
    const store = new Vuex.Store({
      getters: { courseList: () => courses }
    });
    const push = jest.fn();
    const wrapper = mount(Home, {
      localVue,
      store,
      stubs: ['Banner', 'Welcome', 'Notification'],
      mocks: { $router: { push } }
    });
    const card = wrapper.find('.course-card');
    await card.trigger('click');
    expect(push).toHaveBeenCalledWith({
      name: 'Courses',
      query: { id: courses[0].id }
    });
  });
});
