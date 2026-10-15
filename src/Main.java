import org.complex.ComplexNumber;
import org.complex.ComplexMatrix;
import java.util.Scanner;

/// Verifies that the `actual` value is close to the `expected` value
/// within a specified `precision`.
///
/// @param actual    actual value
/// @param expected  reference value
/// @param precision comparison precision
/// @return `true` if `actual` is close to `expected`, otherwise `false`
boolean near(double actual, double expected, double precision)
{
    return Math.abs(actual - expected) < precision;
}

/// Verifies that the `actual` value is close to the `expected` value
/// with a default `precision` of `1e-3`.
///
/// @param actual   actual value
/// @param expected reference value
/// @return `true` if `actual` is close to `expected`, otherwise `false`
boolean near(double actual, double expected)
{
    return near(actual, expected, 1e-3);
}

/// Unit test for [ComplexNumber].
void testComplexNumbers()
{
    try
    {
        // Create three complex numbers
        ComplexNumber a = new ComplexNumber(1, 2);
        ComplexNumber b = new ComplexNumber(-2, -4);
        ComplexNumber c = new ComplexNumber(3);
        ComplexNumber zero = new ComplexNumber();

        // Test getting real and imaginary parts
        assert a.real() == 1.0 : "a.real() is " + a.real();
        assert a.imaginary() == 2.0 : "a.imaginary() is " + a.imaginary();
        assert b.real() == -2.0 : "b.real() is " + b.real();
        assert b.imaginary() == -4.0 : "b.imaginary() is " + b.imaginary();
        assert c.real() == 3.0 : "c.real() is " + c.real();
        assert c.imaginary() == 0 : "c.imaginary() is " + c.imaginary();

        // Test converting to string
        assert a.toString().equals("1.0 + 2.0i") : "a.toString() is " + a.toString();
        assert b.toString().equals("-2.0 - 4.0i") : "b.toString() is " + b.toString();
        assert c.toString().equals("3.0") : "c.toString() is " + c.toString();
        assert zero.toString().equals("0.0") : "zsro.toString() is " + zero.toString();

        // Test modulus
        assert near(a.modulus(), 2.2361) : "a.modulus() is " + a.modulus();
        assert near(b.modulus(), 4.4721) : "b.modulus() is " + b.modulus();
        assert near(c.modulus(), 3.0) : "c.modulus() is " + c.modulus();
        assert near(zero.modulus(), 0) : "zero.modulus() is " + zero.modulus();

        // Test equals
        assert a.equals(new ComplexNumber(1.0, 2.0)) : "a.equals(a) fails";
        assert b.equals(new ComplexNumber(-2.0, -4.0)) : "b.equals(b) fails";
        assert c.equals(new ComplexNumber(3.0)) : "c.equals(c) fails";
        assert c.equals(3.0) : "c.equals(3.0) fails";
        assert !a.equals(zero) : "!a.equals(zero) fails";
        assert !b.equals(zero) : "!b.equals(zero) fails";
        assert !c.equals(zero) : "!c.equals(zero) fails";

        // Test conjugate
        assert a.conjugate().equals(new ComplexNumber(1.0, -2.0)) : "a.conjugate() is " + a.conjugate();
        assert b.conjugate().equals(new ComplexNumber(-2.0, 4.0)) : "b.conjugate() is " + b.conjugate();
        assert c.conjugate().equals(new ComplexNumber(3.0)) : "c.conjugate() is " + c.conjugate();
        assert zero.conjugate().equals(zero) : "zero.conjugate() is " + zero.conjugate();

        // Test adding
        assert a.add(b).equals(new ComplexNumber(-1.0, -2.0)) : "a.add(b) is " + a.add(b);
        assert a.add(c).equals(new ComplexNumber(4.0, 2.0)) : "a.add(c) is " + a.add(c);
        assert b.add(zero).equals(b) : "b.add(zero) is " + b.add(zero);
        assert a.add(5.3).equals(new ComplexNumber(6.3, 2.0)) : "a.add(5.3) is " + a.add(5.3);
        assert a.add(0).equals(a) : "a.add(0) is " + a.add(0);

        // Test subtracting
        assert b.subtract(a).equals(new ComplexNumber(-3.0, -6.0)) : "b.subtract(a) is " + b.subtract(a);
        assert c.subtract(b).equals(new ComplexNumber(5.0, 4.0)) : "c.subtract(b) is " + c.subtract(b);
        assert a.subtract(zero).equals(a) : "a.subtract(zero) is " + a.subtract(zero);
        assert a.subtract(5.3).equals(new ComplexNumber(-4.3, 2.0)) : "a.subtract(5.3) is " + a.subtract(5.3);
        assert b.subtract(0).equals(b) : "b.subtract(0) is " + a.subtract(0);

        // Test multiplication
        assert a.multiply(b).equals(new ComplexNumber(6.0, -8.0)) : "a.multiply(b) is " + a.multiply(b);
        assert b.multiply(c).equals(new ComplexNumber(-6.0, -12.0)) : "b.multiply(c) is " + b.multiply(c);
        assert c.multiply(zero).equals(zero) : "c.multiply(zero) is " + c.multiply(zero);
        assert b.multiply(-1.2).equals(new ComplexNumber(2.4, 4.8)) : "b.multiply(-1.2) is " + b.multiply(-1.2);
        assert a.multiply(0).equals(zero) : "a.multiply(0) is " + a.multiply(0);

        // Test division
        assert b.divide(a).equals(new ComplexNumber(-2.0)) : "b.divide(a) is " + b.divide(a);
        assert a.divide(b).equals(new ComplexNumber(-0.5)) : "a.divide(b) is " + a.divide(b);
        assert c.divide(b).equals(new ComplexNumber(-0.3, -0.6)) : "c.divide(b) is " + c.divide(b);
        assert a.divide(0.5).equals(new ComplexNumber(2.0, 4.0)) : "a.divide(0.5) is " + a.divide(0.5);
        boolean thrown = false;
        try
        {
            b.divide(0);
        }
        catch (ArithmeticException _)
        {
            thrown = true;
        }
        assert thrown : "b.divide(0) does not throw";
        thrown = false;
        try
        {
            b.divide(zero);
        }
        catch (ArithmeticException _)
        {
            thrown = true;
        }
        assert thrown : "b.divide(zero) does not throw";
    }
    catch (AssertionError e)
    {
        System.err.println("FAILED: " + e.getMessage());
        System.exit(1);
    }
    catch (Exception e)
    {
        System.err.println("FAILED: unexpected error: " + e.getMessage());
        System.exit(1);
    }
}

/// Unit test for [ComplexMatrix].
void testComplexMatrix()
{
    try
    {
        // Create simple complex numbers to fill the matrix
        double a0 = 0;
        double a1 = 1;
        double a2 = 2;
        double a3 = 3;
        double a6 = 6;
        ComplexNumber n0 = new ComplexNumber(a0);
        ComplexNumber n1 = new ComplexNumber(a1);
        ComplexNumber n2 = new ComplexNumber(a2);
        ComplexNumber n3 = new ComplexNumber(a3);
        ComplexNumber n6 = new ComplexNumber(a6);

        // Create few types of data to insert into matrices
        ComplexNumber[][] data1 = {
                {n1, n1, n1},
                {n1, n1, n1}
        };
        ComplexNumber[][] data2 = {
                {n2, n2, n2, n2},
                {n2, n2, n2, n2},
                {n2, n2, n2, n2},
        };
        ComplexNumber[][] data3 = {
                {n1, n1},
                {n1, n1},
        };
        ComplexNumber[][] data4 = {
                {n1, n2, n1},
                {n1, n2}
        };
        ComplexNumber[][] data5 = {
                {n2, n2, n2},
                {n2, n2, n2}
        };
        ComplexNumber[][] data6 = {
                {n2, n1},
                {n1, n2},
        };
        ComplexNumber[][] data7 = {
                {n6, n6, n6, n6},
                {n6, n6, n6, n6}
        };

        // Test constructor and getters for numbers of rows and columns and value.
        ComplexMatrix m0 = new ComplexMatrix(2, 3);
        assert m0.rowCount() == 2 : "m0.rowCount is" + m0.rowCount();
        assert m0.columnCount() == 3 : "m0.rowCount is" + m0.columnCount();
        for (int i = 0; i < m0.rowCount(); i++)
        {
            for (int j = 0; j < m0.columnCount(); j++)
            {
                assert m0.value(i, j).equals(n0);
            }
        }

        // Test constructor from data and value getter and setter.
        ComplexMatrix m1 = new ComplexMatrix(data1);
        for (int i = 0; i < m1.rowCount(); i++)
        {
            for (int j = 0; j < m1.columnCount(); j++)
            {
                assert m1.value(i, j).equals(n1);
            }
        }

        // Test transpose method.
        ComplexMatrix m2 = m1.transpose();
        assert m1.rowCount() == m2.columnCount() : "Transpose works incorrectly.";
        assert m2.rowCount() == m1.columnCount() : "Transpose works incorrectly.";
        for (int i = 0; i < m1.rowCount(); i++)
        {
            for (int j = 0; j < m1.columnCount(); j++)
            {
                assert m1.value(i, j).equals(m2.value(j,i)) : "Transpose works incorrectly.";
            }
        }

        // Test isSquare.
        ComplexMatrix m3 = new ComplexMatrix(data3);
        assert m3.isSquare() : "isSquare works incorrectly.";

        // Test determinant.
        ComplexMatrix m4 = new ComplexMatrix(data6);
        assert m4.determinant().equals(n3) : "m4 determinant is" + m4.determinant();

        // Test equals, add and subtract.
        ComplexMatrix m5 = new ComplexMatrix(data1);
        ComplexMatrix m6 = new ComplexMatrix(data5);
        assert m1.equals(m5) : "equals works incorrectly";
        assert m1.add(m5).equals(m6) : "add works incorrectly";
        assert m6.subtract(m1).equals(m5) : "subtract works incorrectly";

        // Test multiply.
        ComplexMatrix m7 = new ComplexMatrix(data2);
        ComplexMatrix m8 = new ComplexMatrix(data7);
        assert m1.multiply(m7).equals(m8) : "Multiply works incorrectly";
    }
    catch (AssertionError e)
    {
        System.err.println("FAILED: " + e.getMessage());
        System.exit(1);
    }
}

/// Executes unit tests.
void tests()
{
    testComplexNumbers();
    testComplexMatrix();
}

/// Prints matrix to console.
/// @param matrix matrix to print
void printMatrix(ComplexMatrix matrix)
{
    for (int i = 0; i < matrix.rowCount(); i++)
    {
        for (int j = 0; j < matrix.columnCount(); j++)
        {
            System.out.print(matrix.value(i, j) + " ");
        }
        System.out.println();
    }
}

/// Parses string and reads complex number.
/// Throws [IllegalArgumentException] if string is not well-formatted.
ComplexNumber parseComplex(String s)
{
    // Three types of regular sentences
    Pattern both = Pattern.compile("^([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+))([+-]\\d+(?:\\.\\d*)?)i$", Pattern.CASE_INSENSITIVE);
    Pattern onlyImag = Pattern.compile("^([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+))i$", Pattern.CASE_INSENSITIVE);
    Pattern onlyReal = Pattern.compile("^([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+))$");

    Matcher mBoth = both.matcher(s);
    if (mBoth.matches())
    {
        return new ComplexNumber(Double.parseDouble(mBoth.group(1)),
                Double.parseDouble(mBoth.group(2)));
    }
    Matcher mImag = onlyImag.matcher(s);
    if (mImag.matches())
    {
        return new ComplexNumber(0, Double.parseDouble(mImag.group(1)));
    }
    Matcher mReal = onlyReal.matcher(s);
    if (mReal.matches())
    {
        return new ComplexNumber(Double.parseDouble(mReal.group(1)));
    }
    throw new IllegalArgumentException("Invalid format");
}

/// Inputs matrix from the user.
ComplexMatrix inputMatrix(Scanner scanner, boolean isSquare)
{
    if (isSquare)
    {
        System.out.print("Input number of rows (columns): ");
    }
    else
    {
        System.out.print("Input number of rows: ");
    }
    int rows = scanner.nextInt();
    int cols = rows;
    if (!isSquare)
    {
        System.out.print("Input number of columns: ");
        cols = scanner.nextInt();
    }
    scanner.nextLine(); // remove trailing '\n' from `in`

    ComplexMatrix matrix = new ComplexMatrix(rows, cols);

    // Regular form for a+bi / a-bi, supports decimal numbers
    Pattern pattern = Pattern.compile(
            "^(?=[+-]?\\d*\\.?\\d*[ij]?$)(?:([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+))?([+-]\\d+(?:\\.\\d*)?)?i?)$\n",
            Pattern.CASE_INSENSITIVE);

    System.out.println("Input matrix " + rows + " x " + cols + ", row by row:");
    System.out.println("- Separate elements by spaces");
    System.out.println("- Each element must be a complex number in form 'a+bi' or 'a-bi'");
    System.out.println("----------------------------------------------------------------");

    for (int i = 0; i < rows; i++)
    {
        System.out.printf("Input %d complex numbers for line %d: ", cols, i + 1);
        String line = scanner.nextLine().trim();
        String[] parts = line.split("\\s+");

        if (parts.length != cols)
        {
            System.out.println("Error: wrong number of elements." +
                    " Expected " + cols + " but " + parts.length + " was given.");
            i--;
            continue;
        }

        boolean ok = true;
        for (int j = 0; j < cols; j++)
        {
            try
            {
                matrix.setValue(i, j, parseComplex(parts[j]));
            }
            catch (Exception _)
            {
                System.out.println("Incorrect number format " + parts[j] + ". " +
                        "Use a+bi or a-bi.");
                ok = false;
                break;
            }
        }
        if (!ok)
        {
            i--; // bad input - repeat line
            continue;
        }
    }
    return matrix;
}

/// Inputs matrix from the user.
ComplexMatrix inputMatrix(Scanner scanner)
{
    return inputMatrix(scanner, false);
}

void handleAdd(Scanner scanner)
{
    System.out.println();
    System.out.println("-----------------");
    System.out.println("> Adding matrices");
    System.out.println("-----------------");
    System.out.println("Input first matrix.");
    ComplexMatrix first = inputMatrix(scanner);
    System.out.println("Input second matrix.");
    ComplexMatrix second = inputMatrix(scanner);
    ComplexMatrix result = first.add(second);
    System.out.println();
    System.out.println("Result (sum) is:");
    printMatrix(result);
    System.out.println();
}

void handleSubtract(Scanner scanner)
{
    System.out.println();
    System.out.println("----------------------");
    System.out.println("> Subtracting matrices");
    System.out.println("----------------------");
    System.out.println("Input first matrix.");
    ComplexMatrix first = inputMatrix(scanner);
    System.out.println("Input second matrix.");
    ComplexMatrix second = inputMatrix(scanner);
    ComplexMatrix result = first.subtract(second);
    System.out.println();
    System.out.println("Result (difference) is:");
    printMatrix(result);
    System.out.println();
}

void handleMultiply(Scanner scanner)
{
    System.out.println();
    System.out.println("----------------------");
    System.out.println("> Multiplying matrices");
    System.out.println("----------------------");
    System.out.println("Input first matrix.");
    ComplexMatrix first = inputMatrix(scanner);
    System.out.println("Input second matrix.");
    ComplexMatrix second = inputMatrix(scanner);
    ComplexMatrix result = first.multiply(second);
    System.out.println();
    System.out.println("Result (product) is:");
    printMatrix(result);
    System.out.println();
}

void handleDivide(Scanner scanner)
{
    System.out.println();
    System.out.println("-------------------");
    System.out.println("> Dividing matrices");
    System.out.println("-------------------");
    System.out.println("Input first matrix.");
    ComplexMatrix first = inputMatrix(scanner);
    System.out.println("Input second matrix (it must be square).");
    ComplexMatrix second = inputMatrix(scanner, true);
    ComplexMatrix result = first.divide(second);
    System.out.println();
    System.out.println("Result (quotient) is:");
    printMatrix(result);
    System.out.println();
}

void handleTranspose(Scanner scanner)
{
    System.out.println();
    System.out.println("--------------------");
    System.out.println("> Transposing matrix");
    System.out.println("--------------------");
    System.out.println("Input a matrix to transpose.");
    ComplexMatrix matrix = inputMatrix(scanner);
    ComplexMatrix result = matrix.transpose();
    System.out.println();
    System.out.println("Result (transposition) is:");
    printMatrix(result);
    System.out.println();
}

void handleInverse(Scanner scanner)
{
    System.out.println();
    System.out.println("------------------");
    System.out.println("> Inverting matrix");
    System.out.println("------------------");
    System.out.println("Input a square matrix to invert.");
    ComplexMatrix matrix = inputMatrix(scanner, true);
    ComplexMatrix result = matrix.invert();
    System.out.println();
    System.out.println("Result (inverse) is:");
    printMatrix(result);
    System.out.println();
}

void handleDeterminant(Scanner scanner)
{
    System.out.println();
    System.out.println("---------------------");
    System.out.println("> Compute determinant");
    System.out.println("---------------------");
    System.out.println("Input a square matrix.");
    ComplexMatrix matrix = inputMatrix(scanner, true);
    ComplexNumber result = matrix.determinant();
    System.out.println();
    System.out.println("Determinant is:");
    System.out.println(result);
    System.out.println();
}

void handleChoiceAndAction()
{
    System.out.println();
    System.out.println("Example program: operation on matrices of complex numbers");
    System.out.println("Author: Sandler Maria. HSE / 2026.");
    System.out.println("==========================================================");
    System.out.println();

    Scanner scanner = new Scanner(System.in);

    while (true)
    {
        // Print intro proposing to choose operation.
        System.out.println("Choose action:");
        System.out.println("1 - Add matrices");
        System.out.println("2 - Subtract matrices");
        System.out.println("3 - Multiply matrices");
        System.out.println("4 - Divide matrices");
        System.out.println("5 - Transpose matrix");
        System.out.println("6 - Inverse square matrix");
        System.out.println("7 - Find determinant");
        System.out.println("0 - Exit");

        try
        {
            int choice = scanner.nextInt();
            scanner.nextLine(); // remove trailing '\n' from `in`
            switch (choice)
            {
                case 0:
                {
                    System.out.println("Bye!");
                    System.exit(0);
                }
                case 1:
                {
                    handleAdd(scanner);
                    break;
                }
                case 2:
                {
                    handleSubtract(scanner);
                    break;
                }
                case 3:
                {
                    handleMultiply(scanner);
                    break;
                }
                case 4:
                {
                    handleDivide(scanner);
                    break;
                }
                case 5:
                {
                    handleTranspose(scanner);
                    break;
                }
                case 6:
                {
                    handleInverse(scanner);
                    break;
                }
                case 7:
                {
                    handleDeterminant(scanner);
                    break;
                }
                default:
                {
                    System.out.println("Error: invalid choice!");
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: something went wrong: " + e.getMessage());
        }
    }
}

void main()
{
    handleChoiceAndAction();
}
