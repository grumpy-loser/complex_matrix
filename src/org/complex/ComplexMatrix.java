package org.complex;

import java.util.Objects;

/// Represents a matrix of complex numbers.
///
/// Initialized by the two integer values representing
/// number of rows and columns, and initially containing
/// zero complex numbers.
///
/// Provides methods to get / modify each matrix cell.
///
/// Matrices support arithmetical operations: [add], [subtract],
/// [multiply], [divide], as well as specific operations
/// like [determinant], [transpose], [invert].
public class ComplexMatrix
{
    /// Two-dimensional array of complex numbers.
    private final ComplexNumber[][] matrix;
    /// Row count.
    private final int rows;
    /// Column count.
    private final int columns;

    /// Constructs matrix of complex numbers with a specified
    /// number of rows and columns.
    ///
    /// All cells are filled with zero complex numbers.
    ///
    /// Throws [IllegalArgumentException] if number of rows or
    /// columns is not a positive number.
    ///
    /// @param rows    number of rows
    /// @param columns number of columns
    public ComplexMatrix(int rows, int columns)
    {
        if (rows <= 0)
        {
            throw new IllegalArgumentException("Rows count must be a positive value");
        }
        if (columns <= 0)
        {
            throw new IllegalArgumentException("Columns count must be a positive value");
        }

        this.rows = rows;
        this.columns = columns;

        this.matrix = new ComplexNumber[rows][columns];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                this.matrix[i][j] = new ComplexNumber();
            }
        }
    }

    /// Constructs matrix of complex numbers from the specified
    /// 2D array of complex numbers.
    ///
    /// Throws [IllegalArgumentException] if array is null or
    /// contains insufficient data.
    ///
    /// @param data 2d-array of complex numbers
    public ComplexMatrix(ComplexNumber[][] data) {
        if (data == null)
        {
            throw new IllegalArgumentException("Null array of data");
        }

        this.rows = data.length;
        if (this.rows == 0)
        {
            throw new IllegalArgumentException("Invalid row count");
        }

        this.columns = data[0].length; // take length of first row as column count
        if (this.columns == 0)
        {
            throw new IllegalArgumentException("Invalid column count");
        }

        this.matrix = new ComplexNumber[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++)
        {
            int length = data[i].length;
            if (length != this.columns)
            {
                throw new IllegalArgumentException("Invalid number of elements in row " + i);
            }
            for (int j = 0; j < length; j++)
            {
                this.matrix[i][j] = new ComplexNumber(data[i][j]);
            }
        }
    }

    /// Returns number of rows.
    public int rowCount()
    {
        return this.rows;
    }

    /// Returns number of columns.
    public int columnCount()
    {
        return this.columns;
    }

    /// Returns complex number in the given matrix cell.
    ///
    /// Throws [IndexOutOfBoundsException] if cell coordinates
    /// are out of range.
    ///
    /// @param row    index of the row
    /// @param column index of the column
    /// @return cell value
    public ComplexNumber value(int row, int column)
    {
        checkRange(row, column);
        return this.matrix[row][column];
    }

    /// Sets complex number as a new value of the given matrix cell.
    ///
    /// Throws [IndexOutOfBoundsException] if cell coordinates
    /// are out of range.
    ///
    /// @param row    index of the row
    /// @param column index of the column
    /// @param value  new cell value
    public void setValue(int row, int column, ComplexNumber value)
    {
        checkRange(row, column);
        this.matrix[row][column] = value;
    }

    /// Returns `true` if this matrix has equal number of rows and columns.
    public boolean isSquare()
    {
        return this.rows == this.columns;
    }

    /// If this matrix is square, computes and returns its determinant.
    /// Otherwise, throws [IllegalStateException].
    public ComplexNumber determinant()
    {
        int r = this.rank(); // throws IllegalStateException if matrix is not square

        if (r == 1)
        {
            return this.matrix[0][0];
        }

        if (r == 2)
        {
            ComplexNumber a = this.matrix[0][0];
            ComplexNumber b = this.matrix[0][1];
            ComplexNumber c = this.matrix[1][0];
            ComplexNumber d = this.matrix[1][1];
            return a.multiply(d).subtract(b.multiply(c));
        }

        ComplexNumber det = new ComplexNumber();
        for (int column = 0; column < r; column++)
        {
            ComplexMatrix minor = this.subMatrix(0, column);
            ComplexNumber cofactorValue = minor.determinant();
            int sign = (column % 2 == 0) ? 1 : -1;
            ComplexNumber term = this.matrix[0][column].multiply(cofactorValue).multiply(sign);
            det = det.add(term);
        }
        return det;
    }

    /// Compares two matrices.
    ///
    /// If matrices do not have equal number of rows and columns,
    /// returns `false`.
    /// Otherwise, returns `true` if all elements of matrix
    /// at the same coordinates are equal, otherwise returns
    /// `false`.
    ///
    /// @param other the matrix to compare with
    /// @return `true` if matrices are equal, otherwise `false`
    public boolean equals(ComplexMatrix other)
    {
        if (this.rows != other.rows || this.columns != other.columns)
        {
            return false;
        }
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < this.columns; j++)
            {
                if (!this.value(i, j).equals(other.value(i, j)))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /// Returns a new matrix which is a transposition of this one.
    ///
    /// Transposed matrix contains the same data but flipped over
    /// the main diagonal of this one, swapping row and column indices.
    ///
    /// Transforms an `m` x `n` matrix into an `n` x `m` matrix by converting
    /// rows into columns and all columns into rows.
    ///
    /// @return transposed matrix
    public ComplexMatrix transpose()
    {
        ComplexMatrix result = new ComplexMatrix(this.columns, this.rows);
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < this.columns; j++)
            {
                result.setValue(j, i, this.value(i, j));
            }
        }
        return result;
    }

    /// Returns new matrix which is an inversion of this one.
    ///
    /// Inverted matrix is a such matrix which, multiplied to
    /// this matrix, produces identity matrix.
    ///
    /// @return inverse matrix
    public ComplexMatrix invert()
    {
        ComplexNumber det = this.determinant();
        if (det.isZero())
        {
            throw new ArithmeticException("Matrix is singular (determinant is zero), inverse does not exist");
        }

        ComplexMatrix adj = this.adjugate();
        ComplexMatrix inv = new ComplexMatrix(this.rows, this.columns);
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < this.columns; j++)
            {
                inv.setValue(i, j, adj.value(i, j).divide(det));
            }
        }
        return inv;
    }

    /// Adds the given complex matrix to this one and returns
    /// the new matrix which is their sum.
    ///
    /// @param other complex matrix being added to this one
    /// @return sum matrix of this and `other` complex matrices
    public ComplexMatrix add(ComplexMatrix other)
    {
        Objects.requireNonNull(other, "Right-hand matrix cannot be null");

        if (this.rows != other.rows || this.columns != other.columns)
        {
            throw new IllegalArgumentException("Matrices have different sizes");
        }

        ComplexMatrix result = new ComplexMatrix(this.rows, this.columns);
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < this.columns; j++)
            {
                result.setValue(i, j, this.value(i, j).add(other.value(i, j)));
            }
        }
        return result;
    }

    /// Subtracts the given complex matrix from this one, and returns
    /// new matrix which is their difference.
    ///
    /// @param other complex matrix being subtracted from this one
    /// @return difference of this and `other` complex matrices
    public ComplexMatrix subtract(ComplexMatrix other)
    {
        Objects.requireNonNull(other, "Right-hand matrix cannot be null");

        if (this.rows != other.rows || this.columns != other.columns)
        {
            throw new IllegalArgumentException("Matrices have different sizes");
        }

        ComplexMatrix result = new ComplexMatrix(this.rows, this.columns);
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < this.columns; j++)
            {
                result.setValue(i, j, this.value(i, j).subtract(other.value(i, j)));
            }
        }
        return result;
    }

    /// Multiplies the given complex matrix to this one, and returns
    /// their product.
    ///
    /// @param other complex matrix being multiplied to this one
    /// @return product of this and `other` matrices
    public ComplexMatrix multiply(ComplexMatrix other)
    {
        Objects.requireNonNull(other, "Right-hand matrix cannot be null");

        if (this.columns != other.rows)
        {
            throw new IllegalArgumentException("Matrices are incompatible");
        }

        ComplexMatrix result = new ComplexMatrix(this.rows, other.columns);
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < other.columns; j++)
            {
                ComplexNumber sum = new ComplexNumber();
                for (int k = 0; k < this.columns; k++)
                {
                    sum = sum.add(this.value(i, k).multiply(other.value(k, j)));
                }
                result.setValue(i, j, sum);
            }
        }
        return result;
    }

    /// Divides this matrix by the given one, and returns
    /// their quotient.
    ///
    /// @param other complex matrix that divides this one
    /// @return quotient of this and `other` matrices
    public ComplexMatrix divide(ComplexMatrix other)
    {
        Objects.requireNonNull(other, "Right-hand matrix cannot be null");

        if (!other.isSquare())
        {
            throw new IllegalArgumentException("Right-hand matrix must be square to compute inverse");
        }

        ComplexNumber det = other.determinant();
        if (det.isZero())
        {
            throw new ArithmeticException("Cannot divide by a singular matrix (determinant of divisor is zero)");
        }

        ComplexMatrix inv = other.invert();
        return this.multiply(inv);
    }

    /// If this matrix is square, returns its linear size
    /// which equals to number of rows (and columns).
    /// Otherwise, throws [IllegalStateException].
    private int rank()
    {
        if (!this.isSquare())
        {
            throw new IllegalStateException("Matrix is not square");
        }
        return this.rows;
    }

    /// Verifies that given `row` and `column` are valid.
    ///
    /// Throws [IndexOutOfBoundsException] if `row` or `column`
    /// is out of range.
    ///
    /// @param row    index of the row
    /// @param column index of the column
    private void checkRange(int row, int column)
    {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns)
        {
            throw new IndexOutOfBoundsException("Out of range");
        }
    }

    /// Return a new matrix which is a minor of this one, with
    /// removed given `row` and `column`.
    ///
    /// @param row    index of row to exclude
    /// @param column index of column to exclude
    /// @return minor matrix
    private ComplexMatrix subMatrix(int row, int column)
    {
        if (this.rows < 2 || this.columns < 2)
        {
            throw new IllegalStateException("Cannot create minor: matrix is too small");
        }

        checkRange(row, column);

        ComplexMatrix minor = new ComplexMatrix(this.rows - 1, this.columns - 1);

        int iMinor = 0;
        for (int i = 0; i < this.rows; i++)
        {
            if (i == row)
            {
                continue; // skip row which is excluded
            }
            int jMinor = 0;
            for (int j = 0; j < this.columns; j++)
            {
                if (j == column)
                {
                    continue; // skip column which is excluded
                }
                minor.setValue(iMinor, jMinor, this.matrix[i][j]);
                jMinor++;
            }
            iMinor++;
        }

        return minor;
    }

    /// Returns adjugate matrix to this one.
    ///
    /// Adjugate matrix is a transpose of the cofactor matrix.
    ///
    /// @return adjugate matrix of this one
    private ComplexMatrix adjugate()
    {
        int r = this.rank();

        ComplexMatrix adj = new ComplexMatrix(r, r);
        for (int i = 0; i < r; i++)
        {
            for (int j = 0; j < r; j++)
            {
                ComplexNumber cof = this.cofactor(i, j);
                adj.setValue(j, i, cof); // i.e. transposed
            }
        }
        return adj;
    }

    /// Compute complex number which is a cofactor value for the matrix
    /// item with given coordinates.
    ///
    /// @param row    index of the row
    /// @param column index of the column
    /// @return computer cofactor value
    private ComplexNumber cofactor(int row, int column)
    {
        ComplexMatrix minor = this.subMatrix(row, column);
        ComplexNumber det = minor.determinant();
        int sign = ((row + column) % 2 == 0) ? 1 : -1;
        return det.multiply(sign);
    }
}
