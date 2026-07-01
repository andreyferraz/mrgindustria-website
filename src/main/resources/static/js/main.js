const body = document.body;
const navToggle = document.querySelector('[data-nav-toggle]');
const navLinks = document.querySelectorAll('.main-nav a');
const currentPage = window.location.pathname.split('/').pop() || 'index.html';

navLinks.forEach((link) => {
  const href = link.getAttribute('href');
  if (href === currentPage || (currentPage === '' && href === 'index.html')) {
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
