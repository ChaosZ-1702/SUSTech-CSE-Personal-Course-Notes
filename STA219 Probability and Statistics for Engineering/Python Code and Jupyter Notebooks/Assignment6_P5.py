import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm
from scipy.integrate import quad
from scipy.optimize import minimize_scalar

# Reject sampling
def reject_sampling(n_samples, mu, sigma, c):
    samples = []
    accepted = 0
    total_trials = 0
    
    while accepted < n_samples:
        batch_size = 100000
        x_prop = np.random.normal(loc=mu, scale=sigma, size=batch_size)
        u = np.random.rand(batch_size)
        
        g_x = norm.pdf(x_prop, loc=mu, scale=sigma)
        accept_prob = fs(x_prop) / (c * g_x)
        
        accept_mask = u <= accept_prob
        accepted_samples = x_prop[accept_mask]
        
        samples.extend(accepted_samples)
        accepted += len(accepted_samples)
        total_trials += batch_size
    
    accept_rate = accepted / total_trials
    return np.array(samples[:n_samples]), accept_rate

fs = lambda x: 0.6 * np.exp(-((x + 5) ** 2) / 2) + 0.4 * np.exp(-((x - 1) ** 2) / 0.5)
c1, _ = quad(fs, -np.inf, np.inf)
mu = -3
sigma = 4
g = lambda x: norm.pdf(x, loc=mu, scale=sigma)
c = 7  # by trial and error

n_samples = 500000
samples, accept_rate = reject_sampling(n_samples, mu, sigma, c)
x = np.linspace(-15, 10, 1000)

# Set plot parameters
plt.figure(figsize=(14.3, 10), dpi=143.4)
plt.rcParams['font.size'] = 16
plt.tight_layout()

# Plot to show that it covers the target distribution, the histogram of the generated samples, and compare it with the theoretical PDF
plt.plot(x, fs(x), label='Target f*(x)')
plt.plot(x, c*g(x), label=f'Proposal cg(x) (c={c})')
plt.hist(samples, bins=200, density=True, alpha=0.6, label='Generated Samples')
plt.plot(x, fs(x)/c1, 'r-', label='Normalized f*(x)', linewidth=2)
plt.xlabel('x')
plt.ylabel('Density')
plt.legend()
plt.title("Target and Proposal Distribution, Sampled Histogram with Theoretical PDF")
plt.grid(True)
plt.savefig('RejectSampling.png')
plt.show()

print(f"Acceptance Rate = {accept_rate:.2%}")