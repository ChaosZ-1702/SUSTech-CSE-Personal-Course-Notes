import math
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import poisson, norm

# Parameters
lambdas = [1, 5, 10, 20]

# Create subplots
fig, axes = plt.subplots(2, 2, figsize=(12, 10))
fig.suptitle('Poisson Distribution with Normal Approximations', fontsize=16)

# Loop through each p value
for idx, (l, ax) in enumerate(zip(lambdas, axes.flat)):
    # Calculate mean and standard deviation for Normal approximation
    mean = l
    std = np.sqrt(l)

    # Define the x range
    lower = math.floor(mean - 5 * std)
    upper = math.ceil(mean + 5 * std)
    x_discrete = np.arange(lower, upper)
    x_continuous = np.arange(lower, upper, 0.01)

    # Poisson Distribution (λ = np)
    poisson_dist = poisson.pmf(x_discrete, mean)

    # Normal Approximation (mean = np, std = sqrt(np(1-p)))
    normal_dist = norm.pdf(x_continuous, mean, std)

    # Plot the distributions
    ax.vlines(x_discrete, ymin=0, ymax=poisson_dist, label='Poisson', color='yellow', linewidth=2)
    ax.plot(x_continuous, normal_dist, '--', label='Normal Approx.', color='red')

    # Set the title and labels
    ax.set_title(f'λ = {l}', fontsize=14)
    ax.set_xlabel('k', fontsize=12)
    ax.set_ylabel('Probability', fontsize=12)

    # Add a legend
    ax.legend()

# Adjust layout for better spacing
plt.tight_layout()
plt.subplots_adjust(top=0.9)

# Save the figure as a file (e.g., PDF format)
plt.savefig('poisson_normal_comparison.pdf')

# Show the plot
plt.show()