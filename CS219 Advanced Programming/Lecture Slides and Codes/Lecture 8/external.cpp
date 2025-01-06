// external.cpp -- external variables
// compile with support.cpp
#include <iostream>
using namespace std;
// external variable
double warming = 0.3; // warming defined
// function prototypes
void update(double dt);
void local();

//int a_int = 1;

int main() // uses global variable
{
    cout << "Global warming is " << warming << " degrees.\n";
    update(0.1); // call function to change warming
    cout << "Global warming is " << warming << " degrees.\n";
    local(); // call function with local warming
    cout << "Global warming is " << warming << " degrees.\n";
    
    //cout<< a_int <<endl;
    
    return 0;
}
