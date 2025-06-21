import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import geom, cauchy

# 设置随机数种子
np.random.seed(20250510)

# Generate uniform samples
n_samples = 10000
unif_samples1 = np.random.uniform(0, 1, n_samples)
unif_samples2 = np.random.uniform(0, 1, n_samples)

# Transform
p_geom = 0.5
geo_samples = np.ceil(np.log(1 - unif_samples1) / np.log(1 - p_geom))
stdcauchy_samples = np.tan(np.pi * (unif_samples2 - 0.5))

# Theoretical PMF/PDF
geo_x = np.arange(min(geo_samples), max(geo_samples))
geo_pmf = geom.pmf(geo_x, p_geom)
stdcauchy_x = np.linspace(-10, 10, 1000)
stdcauchy_pdf = cauchy.pdf(stdcauchy_x)

# Adjust the plots
plt.figure(figsize=(19.5, 9), dpi=143.4)
plt.rcParams['font.size'] = 12
plt.tight_layout()

# Plot - Gerometric(0.5)
plt.subplot(1, 2, 1)
plt.hist(geo_samples, bins=np.arange(1, 17)-0.5, density=True, alpha=0.6, color='skyblue', label='Generated Geometric')
plt.scatter(geo_x, geo_pmf, color='red', s=20, marker='o', label='Theoretical PMF', zorder=2)
plt.xlabel('x')
plt.ylabel('Probability')
plt.title('Geometric(0.5)')
plt.legend()

# Plot - Standard Cauchy Distribution
plt.subplot(1, 2, 2)
plt.hist(stdcauchy_samples, bins=100, density=True, range=(-10, 10), alpha=0.6, color='skyblue', label='Generated Standard Cauchy')
plt.plot(stdcauchy_x, stdcauchy_pdf, 'r-', label='Theoretical PDF')
plt.xlabel('x')
plt.ylabel('Density')
plt.title('Standard Cauchy Distribution')
plt.legend()

# Save the plot for assignment
plt.savefig('Geometric_StdCauchy_Generate.png')

# Show
plt.show()