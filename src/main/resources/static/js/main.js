const body = document.body;
const navToggle = document.querySelector('[data-nav-toggle]');
const navLinks = document.querySelectorAll('.main-nav a');
const normalizePath = (path) => {
  if (!path) return '/';
  const cleaned = path.replace(/\/+$/, '');
  return cleaned === '' ? '/' : cleaned;
};

const currentPath = normalizePath(window.location.pathname);

navLinks.forEach((link) => {
  const href = link.getAttribute('href');
  if (!href || href.startsWith('#')) return;

  const linkPath = normalizePath(new URL(href, window.location.origin).pathname);
  if (linkPath === currentPath) {
    link.classList.add('active');
  }
  link.addEventListener('click', () => body.classList.remove('nav-open'));
});

if (navToggle) {
  navToggle.addEventListener('click', () => body.classList.toggle('nav-open'));
}

const revealElements = document.querySelectorAll('.reveal');
if ('IntersectionObserver' in window) {
  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        entry.target.classList.add('in-view');
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.12 });
  revealElements.forEach((el) => observer.observe(el));
} else {
  revealElements.forEach((el) => el.classList.add('in-view'));
}

const counters = document.querySelectorAll('[data-counter]');
if (counters.length) {
  const counterObserver = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (!entry.isIntersecting) return;
      const el = entry.target;
      const target = Number(el.dataset.counter || 0);
      const suffix = el.dataset.suffix || '';
      const duration = 1200;
      const start = performance.now();
      const update = (now) => {
        const progress = Math.min((now - start) / duration, 1);
        const value = Math.floor(progress * target);
        el.textContent = `${value}${suffix}`;
        if (progress < 1) requestAnimationFrame(update);
      };
      requestAnimationFrame(update);
      counterObserver.unobserve(el);
    });
  }, { threshold: 0.25 });
  counters.forEach((counter) => counterObserver.observe(counter));
}

const forms = document.querySelectorAll('[data-demo-form]');
forms.forEach((form) => {
  form.addEventListener('submit', (event) => {
    event.preventDefault();
    const message = form.querySelector('.form-message');
    if (message) {
      message.textContent = 'Mensagem simulada enviada. Integre este formulário ao backend ou ao WhatsApp oficial da empresa.';
    }
    form.reset();
  });
});

const year = document.querySelector('[data-current-year]');
if (year) year.textContent = new Date().getFullYear();

const adminTabsRoot = document.querySelector('[data-admin-tabs]');
if (adminTabsRoot) {
  const ADMIN_TAB_STORAGE_KEY = 'adminActiveTab';
  const tabLinks = adminTabsRoot.querySelectorAll('[data-admin-tab-target]');
  const tabPanels = adminTabsRoot.querySelectorAll('[data-admin-tab-panel]');

  const activateTab = (tabName) => {
    tabLinks.forEach((tab) => {
      const isActive = tab.dataset.adminTabTarget === tabName;
      tab.classList.toggle('active', isActive);
    });

    tabPanels.forEach((panel) => {
      const isActive = panel.dataset.adminTabPanel === tabName;
      panel.classList.toggle('active', isActive);
    });
  };

  const validTabs = Array.from(tabLinks).map((tab) => tab.dataset.adminTabTarget);
  const tabFromHash = window.location.hash.replace('#', '');
  const tabFromStorage = window.localStorage.getItem(ADMIN_TAB_STORAGE_KEY);
  const initialTab = validTabs.includes(tabFromHash)
    ? tabFromHash
    : (validTabs.includes(tabFromStorage) ? tabFromStorage : 'senha');
  activateTab(initialTab);
  window.localStorage.setItem(ADMIN_TAB_STORAGE_KEY, initialTab);

  tabLinks.forEach((tab) => {
    tab.addEventListener('click', () => {
      const tabName = tab.dataset.adminTabTarget;
      activateTab(tabName);
      window.history.replaceState(null, '', `#${tabName}`);
      window.localStorage.setItem(ADMIN_TAB_STORAGE_KEY, tabName);
    });
  });

  const adminForms = adminTabsRoot.querySelectorAll('form');
  adminForms.forEach((form) => {
    form.addEventListener('submit', () => {
      const activeTab = adminTabsRoot.querySelector('[data-admin-tab-target].active')?.dataset.adminTabTarget;
      if (activeTab) {
        window.localStorage.setItem(ADMIN_TAB_STORAGE_KEY, activeTab);
      }
    });
  });

  const editButtons = adminTabsRoot.querySelectorAll('[data-edit-target]');
  editButtons.forEach((button) => {
    button.addEventListener('click', () => {
      const target = button.dataset.editTarget;
      const form = adminTabsRoot.querySelector(`[data-edit-form="${target}"]`);
      if (!form) return;

      const isOpen = form.classList.contains('open');
      const currentPanel = button.closest('[data-admin-tab-panel]');
      if (currentPanel) {
        currentPanel.querySelectorAll('.admin-edit-form.open').forEach((openedForm) => {
          openedForm.classList.remove('open');
        });
        currentPanel.querySelectorAll('[data-edit-target]').forEach((panelButton) => {
          panelButton.textContent = 'Editar';
        });
      }

      if (!isOpen) {
        form.classList.add('open');
        button.textContent = 'Fechar edição';
      }
    });
  });
}
